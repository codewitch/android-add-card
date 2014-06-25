package com.jeon.thomas.addcard.app;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.jeon.thomas.addcard.app.Card;
import com.jeon.thomas.addcard.app.R;

public class CardFormActivity extends ActionBarActivity {

    private static final String TAG = "CardFormActivity";

    private EditText mCardNumberInput;
    private ImageView mCardTypeImage;
    private EditText mCardExpMonth;
    private EditText mCardExpYear;
    private EditText mCardCVV;
    private Button mSubmitButton;

    private Card mCard = new Card("");

    private static final String DIGIT_REGEX = "\\d+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.card_form_activity_title);
        getActionBar().setIcon(R.drawable.check);
        ImageView view = (ImageView)findViewById(android.R.id.home);
        view.setPadding(10, 10, 10, 10);

        setContentView(R.layout.activity_card_form);

        mCardNumberInput = (EditText)findViewById(R.id.card_number_input);
        mCardNumberInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i2, int i3) {
                mCard.setCardNumber(mCardNumberInput.getText().toString());
                setCardType(mCard.getCardType());
            }
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i2, int i3) {}
            @Override
            public void afterTextChanged(Editable editable) {}
        });

        mCardTypeImage = (ImageView)findViewById(R.id.card_type_image);

        mCardExpMonth = (EditText)findViewById(R.id.exp_month);

        mCardExpYear = (EditText)findViewById(R.id.exp_year);

        mCardCVV = (EditText)findViewById(R.id.cvv);

        mSubmitButton = (Button)findViewById(R.id.submit_button);
        mSubmitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int response = handleCardFormSubmit();
                handleSubmitToast(response);
            }
        });

        setCardType(mCard.getCardType());
    }

    private int handleCardFormSubmit() {

        mCard.setCardNumber(mCardNumberInput.getText().toString());

        if(!mCardExpMonth.getText().toString().matches(DIGIT_REGEX)
                || !mCardExpYear.getText().toString().matches(DIGIT_REGEX)){
            return 20;
        }

        if(!mCardCVV.getText().toString().matches(DIGIT_REGEX)){
            return 30;
        }

        mCard.setExpMonth(Integer.parseInt(mCardExpMonth.getText().toString()));
        mCard.setExpYear(2000 + Integer.parseInt(mCardExpYear.getText().toString()));
        mCard.setCVV(Integer.parseInt(mCardCVV.getText().toString()));

        if (mCard.isCVVValid()) {
            if (mCard.isExpirationValid()) {
                if (mCard.isCardNumberValid()) {
                    return 0;
                } else {
                    return 10;
                }
            } else {
                return 20;
            }
        }

        return 30;
    }

    private void handleSubmitToast(int errorType){
        int messageResId;
        switch (errorType) {
            case 0:
                messageResId = R.string.card_validation_success;
                break;
            case 10:
                messageResId = R.string.card_validation_error_card_number;
                break;
            case 20:
                messageResId = R.string.card_validation_error_exp_date;
                break;
            case 30:
                messageResId = R.string.card_validation_error_cvv;
                break;
            default:
                messageResId = R.string.card_validation_error_default;
                break;
        }

        Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
    }

    private void setCardType(int cardType_id) {
        int imageId;

        switch (cardType_id) {
            case R.string.visa:
                imageId = R.drawable.bt_visa;
                break;
            case R.string.mastercard:
                imageId = R.drawable.bt_mastercard;
                break;
            case R.string.amex:
                imageId = R.drawable.bt_amex;
                break;
            case R.string.diners_club:
                imageId = R.drawable.bt_diners_club;
                break;
            case R.string.discover:
                imageId = R.drawable.bt_discover;
                break;
            case R.string.jcb:
                imageId = R.drawable.bt_jcb;
                break;
            default:
                imageId = R.drawable.bt_generic_card;
                break;
        }

        mCardTypeImage.setImageResource(imageId);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.card_form, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart() called");
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause() called");
    }

    @Override
    public void onResume() {
        super.onResume();
        Log.d(TAG, "onResume() called");
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d(TAG, "onStop() called");
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy() called");
    }
}
