package mreverter.forms.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import mreverter.forms.R;
import mreverter.forms.examples.cardNumber.CardNumberDataListener;
import mreverter.forms.examples.cardNumber.CardNumberDotsMask;
import mreverter.forms.examples.cardNumber.CardNumberMask;
import mreverter.forms.examples.securityCode.SecurityCodeDataListener;
import mreverter.forms.examples.securityCode.SecurityCodeDotsMask;
import mreverter.forms.utils.JsonUtil;

public class CardRepresentationFragment extends Fragment implements CardNumberDataListener, SecurityCodeDataListener{

    private View mCardBack;
    private View mCardFront;

    private TextView mMaskedCardNumber;
    private TextView mMaskedSecurityCode;

    private CardNumberDotsMask mCardNumberMask;
    private SecurityCodeDotsMask mSecurityCodeMask;

    public CardRepresentationFragment() {
        // Required empty public constructor
    }

    public static CardRepresentationFragment newInstance(CardNumberMask cardNumberMask, SecurityCodeDotsMask securityCodeMask) {
        CardRepresentationFragment fragment = new CardRepresentationFragment();
        Bundle args = new Bundle();
        args.putString("cardNumberMask", JsonUtil.getInstance().toJson(cardNumberMask));
        args.putString("securityCodeMask", JsonUtil.getInstance().toJson(securityCodeMask));

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mCardNumberMask = JsonUtil.getInstance().fromJson(getArguments().getString("cardNumberMask"), CardNumberDotsMask.class);
            mSecurityCodeMask = JsonUtil.getInstance().fromJson(getArguments().getString("securityCodeMask"), SecurityCodeDotsMask.class);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_representation, container, false);
    }
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initializeControls();
    }

    private void initializeControls() {

        mCardFront = getView().findViewById(R.id.frontCardRepresentation);
        mCardBack = getView().findViewById(R.id.backCardRepresentation);

        mMaskedCardNumber = (TextView) getView().findViewById(R.id.maskedCardNumber);
        mMaskedCardNumber.setText(mCardNumberMask.getMaskedText());

        mMaskedSecurityCode = (TextView) getView().findViewById(R.id.maskedSecurityCode);
        mMaskedSecurityCode.setText(mSecurityCodeMask.getMaskedText());

        mCardFront.setVisibility(View.VISIBLE);
        mCardBack.setVisibility(View.GONE);

    }

    @Override
    public void onCardDataChanged(String cardNumber) {
        mCardNumberMask.setUnmaskedText(cardNumber);
        mMaskedCardNumber.setText(mCardNumberMask.getMaskedText());
    }

    @Override
    public void onSecurityCodeDataChanged(String securityCode) {
        mSecurityCodeMask.setUnmaskedText(securityCode);
        mMaskedSecurityCode.setText(mSecurityCodeMask.getMaskedText());
    }

    public CardNumberMask getMask() {
        return mCardNumberMask;
    }

    public void onSecurityCodeFocused() {
        mCardFront.setVisibility(View.GONE);
        mCardBack.setVisibility(View.VISIBLE);
    }

    public void onSecurityCodeUnfocused() {
        mCardFront.setVisibility(View.VISIBLE);
        mCardBack.setVisibility(View.GONE);
    }
}
