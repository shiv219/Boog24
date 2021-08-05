package com.boog24.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.databinding.DataBindingUtil;

import com.boog24.R;
import com.boog24.adapter.BusinessAdapter;
import com.boog24.adapter.CityAdapter;
import com.boog24.databinding.ActivityRecommendSalonBinding;
import com.boog24.extra.BaseActivity;
import com.boog24.extra.NetworkAlertUtility;
import com.boog24.modals.CommonOffset;
import com.boog24.modals.getCitiesList.CategoryDatum;
import com.boog24.modals.getCitiesList.CityDatum;
import com.boog24.modals.getCitiesList.Result;
import com.boog24.presenter.GetCityPresenter;
import com.boog24.presenter.GetCommonDataPresenter;
import com.boog24.view.ICommonView;
import com.boog24.view.IGetCityView;
import com.pixplicity.easyprefs.library.Prefs;

import java.util.ArrayList;
import java.util.List;

public class RecommendSalonActivity extends BaseActivity implements IGetCityView, ICommonView {

    ActivityRecommendSalonBinding binding;
    GetCityPresenter getCityPresenter;
    GetCommonDataPresenter getCommonDataPresenter;
    String cityId = "", categoryId = "", speak = "";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recommend_salon);
        binding.setActivity(this);

        getCityPresenter = new GetCityPresenter();
        getCityPresenter.setView(this);


        getCommonDataPresenter = new GetCommonDataPresenter();
        getCommonDataPresenter.setView(this);

        if (NetworkAlertUtility.isConnectingToInternet(RecommendSalonActivity.this)) {
            getCityPresenter.getCity(RecommendSalonActivity.this);
        } else {
            NetworkAlertUtility.showNetworkFailureAlert(RecommendSalonActivity.this);
        }


        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        setSpeak();
        setTypeofBusiness();
        setCity();


//        binding.spinerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
//            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
//                CityDatum mSelected = (CityDatum) parent.getItemAtPosition(pos);
//                Log.i("Id:", mSelected.getCityName());
//                if (!mSelected.getCityId().equalsIgnoreCase("-1")) {
//                    cityId = mSelected.getCityId();
//                    binding.edtCity.setText(mSelected.getCityName());
//                }
//            }
//
//            public void onNothingSelected(AdapterView<?> parent) {
//
//            }
//        });

        binding.spineerType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                CategoryDatum mSelected = (CategoryDatum) parent.getItemAtPosition(pos);
                Log.i("Id:", mSelected.getCategoryName());
                if (!mSelected.getCategoryId().equalsIgnoreCase("-1")) {
                    categoryId = mSelected.getCategoryId();
                    binding.edtType.setText(mSelected.getCategoryName());
                }
            }

            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        binding.spineerSpeak.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                if (i == 0) {

                } else if (i == 1) {
                    speak = "yes";
                    binding.edtSpeak.setText("Yes");
                } else {
                    speak = "no";
                    binding.edtSpeak.setText("No");
                }

            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    public void onClick(View view) {
        Intent i;
        switch (view.getId()) {

            case R.id.edtSpeak:
                binding.spineerSpeak.performClick();
                break;
            case R.id.edtType:
                binding.spineerType.performClick();
                break;
            case R.id.btnSubmit:

                if (binding.etSalonName.getText().toString().equalsIgnoreCase("")) {
                    binding.etSalonName.setError(getString(R.string.pls_enter_salon_name));
                    binding.etSalonName.requestFocus();
                    return;
                }
                if (speak.equalsIgnoreCase("")) {
                    windowPopUp(this, getString(R.string.pls_select_you_have_speak));
                    return;
                }
                if (binding.etContactPerson.getText().toString().equalsIgnoreCase("")) {
                    binding.etContactPerson.setError(getString(R.string.pls_enter_contact_person));
                    binding.etContactPerson.requestFocus();
                    return;
                }
                if (categoryId.equalsIgnoreCase("")) {
                    windowPopUp(this, getString(R.string.pls_select_business));
                    return;
                }

//                if (binding.etStreet.getText().toString().equalsIgnoreCase("")){
//                    binding.etStreet.setError(getString(R.string.pls_enter_street));
//                    binding.etStreet.requestFocus();
//                    return;
//                }
//
//                if (binding.etPhone.getText().toString().equalsIgnoreCase("")){
//                    binding.etPhone.setError(getString(R.string.pls_enter_salon_phone));
//                    binding.etPhone.requestFocus();
//                    return;
//                }
                if (binding.edtCity.getText().toString().equalsIgnoreCase("")) {
                    binding.edtCity.setError(getString(R.string.pls_enter_city));
                    binding.edtCity.requestFocus();

                    return;
                }
                if (binding.etFirstName.getText().toString().equalsIgnoreCase("")) {
                    binding.etFirstName.setError(getString(R.string.pls_enter_first_name));
                    binding.etFirstName.requestFocus();
                    return;
                }
                if (binding.etLastName.getText().toString().equalsIgnoreCase("")) {
                    binding.etLastName.setError(getString(R.string.pls_enter_last_name));
                    binding.etLastName.requestFocus();
                    return;
                }
                if (binding.etEmail.getText().toString().equalsIgnoreCase("")) {
                    binding.etEmail.setError(getString(R.string.pls_enter_email));
                    binding.etEmail.requestFocus();
                    return;
                }
                if (NetworkAlertUtility.isConnectingToInternet(RecommendSalonActivity.this)) {
                    getCommonDataPresenter.getRecommendSalon(RecommendSalonActivity.this, binding.etEmail.getText().toString(),
                            binding.etFirstName.getText().toString(), binding.etLastName.getText().toString(), binding.etPhone.getText().toString(),
                            binding.etSalonName.getText().toString(), binding.etStreet.getText().toString(), binding.edtCity.getText().toString(), speak, categoryId,
                            binding.etZipCode.getText().toString(),
                            binding.etContactPerson.getText().toString());
                } else {
                    NetworkAlertUtility.showNetworkFailureAlert(RecommendSalonActivity.this);
                }

                break;
        }
    }


    private void setSpeak() {

        // Spinner Drop down elements
        List<String> userList3 = new ArrayList<>();
//        EmployeeDatum user3 = new EmployeeDatum(getResources().getString(R.string.did_you_speak),"-1");
        userList3.add(getResources().getString(R.string.did_you_informed));
        userList3.add("Yes");
        userList3.add("No");

        ArrayAdapter<String> dataAdapter3 = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, userList3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spineerSpeak.setAdapter(dataAdapter3);

    }

    private void setTypeofBusiness() {
        // Spinner Drop down elements
        List<CategoryDatum> userList3 = new ArrayList<>();
        CategoryDatum user3 = new CategoryDatum("-1", getResources().getString(R.string.type_of_business));
        userList3.add(user3);

        ArrayAdapter<CategoryDatum> dataAdapter3 = new ArrayAdapter<CategoryDatum>(this, android.R.layout.simple_spinner_item, userList3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spineerType.setAdapter(dataAdapter3);

    }

    private void setCity() {
        // Spinner Drop down elements
        List<CityDatum> userList3 = new ArrayList<>();
        CityDatum user3 = new CityDatum("-1", getResources().getString(R.string.city));
        userList3.add(user3);

        ArrayAdapter<CityDatum> dataAdapter3 = new ArrayAdapter<CityDatum>(this, android.R.layout.simple_spinner_item, userList3);
        dataAdapter3.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
//        binding.spinerCity.setAdapter(dataAdapter3);


    }

    @Override
    public void onGetCity(Result response) {
        if (response.getStatus() == 200) {

            //For CIty
            ArrayList<CityDatum> customerTypeDatumArrayList = new ArrayList<>();
            customerTypeDatumArrayList.add(new CityDatum("-1", getResources().getString(R.string.city)));

            if (response.getCityData().size() > 0) {
                for (int i = 0; i < response.getCityData().size(); i++) {
                    customerTypeDatumArrayList.add(new CityDatum(response.getCityData().get(i).getCityId(), response.getCityData().get(i).getCityName()));
                }
            }
            CityAdapter statusAdpter = new CityAdapter(RecommendSalonActivity.this, R.layout.spinner_item_row,
                    customerTypeDatumArrayList);
//            binding.spinerCity.setAdapter(statusAdpter);


            //For Category
            ArrayList<CategoryDatum> categoryDatumArrayList = new ArrayList<>();
            categoryDatumArrayList.add(new CategoryDatum("-1", getResources().getString(R.string.type_of_business)));

            if (response.getCategoryData().size() > 0) {
                for (int i = 0; i < response.getCategoryData().size(); i++) {
                    categoryDatumArrayList.add(new CategoryDatum(response.getCategoryData().get(i).getCategoryId(), response.getCategoryData().get(i).getCategoryName()));
                }
            }
            BusinessAdapter businessAdapter = new BusinessAdapter(RecommendSalonActivity.this, R.layout.spinner_item_row,
                    categoryDatumArrayList);
            binding.spineerType.setAdapter(businessAdapter);

        } else if (response.getStatus() == 400) {


        } else if (response.getStatus() == 406) {
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        } else {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Failure")
                    .setMessage(response.getMessage())
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show().setCanceledOnTouchOutside(false);
        }
    }

    @Override
    public Context getContext() {
        return null;
    }

    @Override
    public void onGetDetail(CommonOffset response) {
        if (response.getStatus() == 200) {
            final Dialog myDialog = new Dialog(this);
            myDialog.setContentView(R.layout.alert_label_editor);
            //  myDialog.setCanceledOnTouchOutside(false);
            TextView popText = myDialog.findViewById(R.id.popText);
            popText.setText(response.getMessage());
            Button btnCancel = myDialog.findViewById(R.id.btnCancel);
            Button btnLogin = myDialog.findViewById(R.id.btnLogin);
            btnLogin.setVisibility(View.GONE);
            btnCancel.setText(getResources().getString(R.string.ok));
            btnCancel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    myDialog.dismiss();
                    finish();
                }
            });
            myDialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            myDialog.show();
        } else if (response.getStatus() == 400) {


        } else if (response.getStatus() == 406) {
            Prefs.clear();
            startActivity(new Intent(this, LoginActivity.class).setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_CLEAR_TOP));
            finish();
        } else {
            new AlertDialog.Builder(this).setIcon(android.R.drawable.ic_dialog_alert).setTitle("Failure")
                    .setMessage(response.getMessage())
                    .setPositiveButton(getResources().getString(R.string.ok), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    }).show().setCanceledOnTouchOutside(false);
        }

    }
}
