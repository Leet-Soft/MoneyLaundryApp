package uni.fmi.masters.moneylaundryapp.ui;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import java.util.ArrayList;

import uni.fmi.masters.moneylaundryapp.R;
import uni.fmi.masters.moneylaundryapp.entity.AccountEntity;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AccountFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AccountFragment extends Fragment {


    Spinner accountS;
    ArrayAdapter<AccountEntity> adapter;
    ArrayList<AccountEntity> accounts;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_account, container, false);

        accounts = new ArrayList<>();
        AccountEntity a1 = new AccountEntity();
        a1.setName("Test");

        accounts.add(a1);
        AccountEntity a2 = new AccountEntity();
        a1.setName("Test2");

        accounts.add(a2);

        accountS = view.findViewById(R.id.accountS);
        adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_list_item_1);

        return view;
    }

    public AccountFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment AccountFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static AccountFragment newInstance(String param1, String param2) {
        AccountFragment fragment = new AccountFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }


}