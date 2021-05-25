package com.boog24.fragment;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.databinding.DataBindingUtil;

import com.boog24.R;
import com.boog24.activity.AboutUsActivity;
import com.boog24.activity.ChangeLanguageActivity;
import com.boog24.activity.ContactUsActivity;
import com.boog24.activity.EditProfileActivity;
import com.boog24.activity.LoginActivity;
import com.boog24.activity.MainActivity;
import com.boog24.activity.NotificationActivity;
import com.boog24.activity.RecommendSalonActivity;
import com.boog24.activity.WishlistActivity;
import com.boog24.custom.Constants;
import com.boog24.custom.ImageCropActivity;
import com.boog24.databinding.FragmentProfileBinding;
import com.boog24.extra.BaseFragment;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.CommonOffset;
import com.boog24.presenter.GetCommonDataPresenter;
import com.boog24.view.ICommonView;
import com.pixplicity.easyprefs.library.Prefs;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class ProfileFragment extends BaseFragment implements View.OnClickListener, ICommonView {

    FragmentProfileBinding binding;

    private static final int PERMISSIONS_MULTIPLE_REQUEST = 123;
    public static Bitmap bitmap;
    public static final int CROP_IMAGE_REQUEST = 654;
    private static final String DELETE_ACCOUNT_API = "DELETE_ACCOUNT_API";
    Uri imgUri;
    String encodedImage;
    GetCommonDataPresenter getCommonDataPresenter;
    String apiType = "";

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser) {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false);
        View v = binding.getRoot();

        getCommonDataPresenter = new GetCommonDataPresenter();
        getCommonDataPresenter.setView(this);

        binding.tvEdit.setOnClickListener(this);
        binding.tvMyBooking.setOnClickListener(this);
        binding.tvWishlist.setOnClickListener(this);
        binding.tvLanguage.setOnClickListener(this);
        binding.tvAbout.setOnClickListener(this);
        binding.tvContact.setOnClickListener(this);
        binding.btnLogout.setOnClickListener(this);
        binding.tvRecommend.setOnClickListener(this);
        binding.tvNotification.setOnClickListener(this);
        binding.tvDelete.setOnClickListener(this);

        binding.civUser.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                apiType = "image";
                checkPermissions();
            }
        });


        binding.switchNotification.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
                    apiType = "Notification";
                    if (b)
                        getCommonDataPresenter.updateNotificationStatus(getActivity(), "1");
                    else
                        getCommonDataPresenter.updateNotificationStatus(getActivity(), "0");
                } else {
                    NetworkAlertUtility.showNetworkFailureAlert(getActivity());
                }

            }
        });



        return v;
    }

    @Override
    public void onResume() {
        super.onResume();

        if (!Prefs.getString(Constants.SharedPreferences_loginKey, "").equalsIgnoreCase("")) {
            binding.tvDelete.setVisibility(View.VISIBLE);
            binding.viewDelete.setVisibility(View.VISIBLE);
            binding.tvName.setText(Prefs.getString(Constants.SharedPreferences_full_name, ""));
            if (Prefs.getString(Constants.SharedPreferences_Gender, "").equals("F")) {
                binding.tvGender.setText(getResources().getString(R.string.female));
            } else {
                binding.tvGender.setText(getResources().getString(R.string.male));
            }
            //binding.tvGender.setText(Prefs.getString(Constants.SharedPreferences_Gender, ""));
            binding.tvEmail.setText(Prefs.getString(Constants.SharedPreferences_Email, ""));
            if (Prefs.getString(Constants.SharedPreferences_notification_status, "").equalsIgnoreCase("1")) {
                binding.switchNotification.setChecked(true);
            } else {
                binding.switchNotification.setChecked(false);
            }
            Picasso.get().load(Prefs.getString(Constants.SharedPreferences_profileimage, ""))
                    .error(R.mipmap.ic_launcher)
                    .placeholder(R.mipmap.ic_launcher)
                    .into(binding.civUser);
        } else {
            binding.btnLogout.setText(getResources().getString(R.string.login));
            binding.tvName.setText("");
            binding.tvGender.setText("");
            binding.tvEmail.setText("");
            binding.tvDelete.setVisibility(View.GONE);
            binding.viewDelete.setVisibility(View.GONE);
        }
    }

    public static ProfileFragment newInstance() {
        ProfileFragment f = new ProfileFragment();
        return f;
    }

    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tvEdit:
                if (!Prefs.getString(Constants.SharedPreferences_loginKey, "").equalsIgnoreCase("")) {
                    Intent intent = new Intent(getActivity(), EditProfileActivity.class);
                    startActivity(intent);
                } else {
                    windowPopUpForLogin(getActivity(), getActivity().getResources().getString(R.string.pls_login_first));
                }
                break;

            case R.id.tvMyBooking:
                ((MainActivity) getActivity()).viewPager.setCurrentItem(1);
                break;

            case R.id.tvWishlist:
                if (!Prefs.getString(Constants.SharedPreferences_loginKey, "").equalsIgnoreCase("")) {
                    Intent intent1 = new Intent(getActivity(), WishlistActivity.class);
                    startActivity(intent1);
                } else {
                    windowPopUpForLogin(getActivity(), getActivity().getResources().getString(R.string.pls_login_first));
                }
                break;

            case R.id.tvLanguage:
                Intent intent2 = new Intent(getActivity(), ChangeLanguageActivity.class);
                startActivity(intent2);
                break;
            case R.id.tvAbout:
                Intent intent3 = new Intent(getActivity(), AboutUsActivity.class);
                intent3.putExtra("what", "about");
                startActivity(intent3);
                break;
            case R.id.tvContact:
                Intent intent4 = new Intent(getActivity(), ContactUsActivity.class);
                startActivity(intent4);
                break;
            case R.id.btnLogout:
                if (!Prefs.getString(Constants.SharedPreferences_loginKey, "").equalsIgnoreCase("")) {
                    apiType = "logout";
                    if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
                        getCommonDataPresenter.logout(getActivity());
                    } else {
                        NetworkAlertUtility.showNetworkFailureAlert(getActivity());
                    }
                } else {
                    String lang = Prefs.getString(Constants.SharedPreferences_Langauge, "");
                    Prefs.clear();
                    startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("lang", lang));
                    getActivity().finish();
                }

                break;

            case R.id.tvRecommend:
                Intent intent5 = new Intent(getActivity(), RecommendSalonActivity.class);
                startActivity(intent5);
                break;
            case R.id.tvNotification:
                Intent intent6 = new Intent(getActivity(), NotificationActivity.class);
                startActivity(intent6);
                break;

            case R.id.tvDelete:
                    new AlertDialog.Builder(requireActivity()).setIcon(android.R.drawable.ic_dialog_alert).setTitle(getString(R.string.delete_account))
                            .setMessage(getString(R.string.are_you_sure_delete_account)).setNegativeButton(getResources().getString(R.string.cancel_delete_account), (dialog, which) -> dialog.cancel())
                            .setPositiveButton(getResources().getString(R.string.delete), new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    apiType = DELETE_ACCOUNT_API;
                                    getCommonDataPresenter.deleteMyAccount(requireActivity());

                                }
                            }).show().setCanceledOnTouchOutside(true);

                    break;

        }
    }


    @RequiresApi(api = Build.VERSION_CODES.M)
    private void checkPermissions() {
        if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (getActivity(), Manifest.permission.READ_EXTERNAL_STORAGE)) {
                requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSIONS_MULTIPLE_REQUEST);


            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSIONS_MULTIPLE_REQUEST);
                }
            }
        } else if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {

            if (ActivityCompat.shouldShowRequestPermissionRationale
                    (getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSIONS_MULTIPLE_REQUEST);


            } else {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                    requestPermissions(new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA}, PERMISSIONS_MULTIPLE_REQUEST);
                }
            }
        } else if (CropImage.isExplicitCameraPermissionRequired(getActivity())) {
            ActivityCompat.requestPermissions(getActivity(),
                    new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                    CropImage.CAMERA_CAPTURE_PERMISSIONS_REQUEST_CODE);
        } else {
            CropImage.startPickImageActivity(getActivity(), ProfileFragment.this);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Log.e("TAG", "onActivityResult: FRAGMENT");
        if (requestCode == CropImage.PICK_IMAGE_CHOOSER_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Uri imageUri = CropImage.getPickImageResultUri(getActivity(), data);

            boolean requirePermissions = false;
            if (CropImage.isReadExternalStoragePermissionsRequired(getActivity(), imageUri)) {

                requirePermissions = true;

                Intent intent = new Intent(getActivity(), ImageCropActivity.class);
                intent.putExtra("image_uri", imageUri);
                startActivityForResult(intent, CROP_IMAGE_REQUEST);

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        CropImage.PICK_IMAGE_PERMISSIONS_REQUEST_CODE);
            } else {

                Intent intent = new Intent(getActivity(), ImageCropActivity.class);
                intent.putExtra("image_uri", imageUri);
                startActivityForResult(intent, CROP_IMAGE_REQUEST);
            }
        } else if (requestCode == CROP_IMAGE_REQUEST && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                Log.e("TGG", "onActivityResult: " + data.getStringExtra("image_uri"));
                Uri selectedImage = Uri.parse(data.getStringExtra("image_uri"));
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), selectedImage);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ByteArrayOutputStream bytes = new ByteArrayOutputStream();
                bitmap.compress(Bitmap.CompressFormat.JPEG, 90, bytes);
                Log.e("Activity", "Pick from Gallery::>>> ");
                byte[] ba = bytes.toByteArray();
                imgUri = getImageUri(getActivity(), bitmap);
                encodedImage = Base64.encodeToString(ba, Base64.DEFAULT);
                // todo Add images


                binding.civUser.setImageBitmap(bitmap);
                if (NetworkAlertUtility.isConnectingToInternet(getActivity())) {
                    getCommonDataPresenter.update_customer_image(getActivity(), encodedImage, imgUri);
                } else {
                    NetworkAlertUtility.showNetworkFailureAlert(getActivity());
                }

            } else {
                Log.d("TAG", "crop result is null");
            }
        }
    }

    public Uri getImageUri(Context inContext, Bitmap inImage) {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        inImage.compress(Bitmap.CompressFormat.JPEG, 100, bytes);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), inImage, "Title", null);
        return Uri.parse(path);
    }


    @Override
    public void onGetDetail(CommonOffset response) {
        if (apiType.equalsIgnoreCase("image")) {
            if (response.getStatus() == 200) {

                windowPopUp(getActivity(), response.getMessage());
                Prefs.putString(Constants.SharedPreferences_profileimage, response.getProfileimage());

                Picasso.get().load(Prefs.getString(Constants.SharedPreferences_profileimage, ""))
                        .error(R.mipmap.ic_launcher)
                        .placeholder(R.mipmap.ic_launcher)
                        .into(binding.civUser);

            } else if (response.getStatus() == 406) {
                Prefs.clear();
                startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                getActivity().finish();
            } else {
                new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Failure")
                        .setMessage(response.getMessage())
                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show().setCanceledOnTouchOutside(false);
            }
        } else if (apiType.equalsIgnoreCase("Notification")) {
            if (response.getStatus() == 200) {

                Prefs.putString(Constants.SharedPreferences_notification_status, response.getUserDetail().getUser_notification_status());

            } else if (response.getStatus() == 406) {
                Prefs.clear();
                startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
                getActivity().finish();
            } else {
                new AlertDialog.Builder(getActivity()).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Failure")
                        .setMessage(response.getMessage())
                        .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        }).show().setCanceledOnTouchOutside(false);
            }
        } else if (apiType.equalsIgnoreCase(DELETE_ACCOUNT_API)) {

            if (response.getMessage().equalsIgnoreCase("Your account cannot be deleted because you have outstanding bookings")) {
                Toast.makeText(requireActivity(), response.getMessage(), Toast.LENGTH_SHORT).show();
            } else {
                String lang = Prefs.getString(Constants.SharedPreferences_Langauge, "");
                Prefs.clear();
                startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("lang", lang));
                getActivity().finish();
            }
        } else {
            String lang = Prefs.getString(Constants.SharedPreferences_Langauge, "");
            Prefs.clear();
            startActivity(new Intent(getActivity(), LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP).putExtra("lang", lang));
            getActivity().finish();
        }
    }

}
