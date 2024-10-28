package com.example.expensemanager;

import android.app.AlertDialog;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;




import java.text.DateFormat;
import java.util.Date;

import com.example.expensemanager.Model.Data;

/**
 * A simple {@link Fragment} subclass.
 */
public class DashBoardFragment extends Fragment {

    // Floating button
    private FloatingActionButton fabMainBtn;
    private FloatingActionButton fabIncomeBtn;
    private FloatingActionButton fabExpenseBtn;

    // Floating button text views
    private TextView fabIncomeTxt;
    private TextView fabExpenseTxt;

    // Boolean to track button state
    private boolean isOpen = false;

    // Animations
    private Animation fadeOpen, fadeClose;

    // Dashboard income and expense result
    private TextView totalIncomeResult;
    private TextView totalExpenseResult;

    // Firebase
    private FirebaseAuth mAuth;
    private DatabaseReference mIncomeDatabase;
    private DatabaseReference mExpenseDatabase;

    // Recycler views
    private RecyclerView mRecyclerIncome;
    private RecyclerView mRecyclerExpense;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View myView = inflater.inflate(R.layout.fragment_dash_board, container, false);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser mUser = mAuth.getCurrentUser();
        String uid = mUser.getUid();

        mIncomeDatabase = FirebaseDatabase.getInstance().getReference().child("IncomeData").child(uid);
        mExpenseDatabase = FirebaseDatabase.getInstance().getReference().child("ExpenseDatabase").child(uid);

        mIncomeDatabase.keepSynced(true);
        mExpenseDatabase.keepSynced(true);

        // Connect floating buttons to layout
        fabMainBtn = myView.findViewById(R.id.fb_main_plus_btn);
        fabIncomeBtn = myView.findViewById(R.id.income_Ft_btn);
        fabExpenseBtn = myView.findViewById(R.id.expense_Ft_btn);

        // Connect floating text
        fabIncomeTxt = myView.findViewById(R.id.income_ft_text);
        fabExpenseTxt = myView.findViewById(R.id.expense_ft_text);

        // Total income and expense result set
        totalIncomeResult = myView.findViewById(R.id.income_set_result);
        totalExpenseResult = myView.findViewById(R.id.expense_set_result);

        // Animation connect
        fadeOpen = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_open);
        fadeClose = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_close);

        fabMainBtn.setOnClickListener(view -> {
            addData();
            toggleFloatingButtons();
        });

        // Calculate total income
        mIncomeDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int totalSum = 0;
                for (DataSnapshot mySnap : dataSnapshot.getChildren()) {
                    Data data = mySnap.getValue(Data.class);
                    totalSum += data.getAmount();
                }
                totalIncomeResult.setText(String.valueOf(totalSum) + ".00");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error if needed
            }
        });

        // Calculate total expense
        mExpenseDatabase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                int totalSum = 0;
                for (DataSnapshot mySnapshot : dataSnapshot.getChildren()) {
                    Data data = mySnapshot.getValue(Data.class);
                    totalSum += data.getAmount();
                }
                totalExpenseResult.setText(String.valueOf(totalSum) + ".00");
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Handle error if needed
            }
        });

        // Set up Recycler views
        setupRecyclerViews(myView);

        return myView;
    }

    private void setupRecyclerViews(View myView) {
        mRecyclerIncome = myView.findViewById(R.id.recycler_income);
        mRecyclerExpense = myView.findViewById(R.id.recycler_epense);

        // Recycler view for income
        LinearLayoutManager layoutManagerIncome = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerIncome.setReverseLayout(true);
        layoutManagerIncome.setStackFromEnd(true);
        mRecyclerIncome.setHasFixedSize(true);
        mRecyclerIncome.setLayoutManager(layoutManagerIncome);

        // Recycler view for expense
        LinearLayoutManager layoutManagerExpense = new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false);
        layoutManagerExpense.setReverseLayout(true);
        layoutManagerExpense.setStackFromEnd(true);
        mRecyclerExpense.setHasFixedSize(true);
        mRecyclerExpense.setLayoutManager(layoutManagerExpense);
    }

    private void toggleFloatingButtons() {
        if (isOpen) {
            fabIncomeBtn.startAnimation(fadeClose);
            fabExpenseBtn.startAnimation(fadeClose);
            fabIncomeBtn.setClickable(false);
            fabExpenseBtn.setClickable(false);
            fabIncomeTxt.startAnimation(fadeClose);
            fabExpenseTxt.startAnimation(fadeClose);
            fabIncomeTxt.setClickable(false);
            fabExpenseTxt.setClickable(false);
            isOpen = false;
        } else {
            fabIncomeBtn.startAnimation(fadeOpen);
            fabExpenseBtn.startAnimation(fadeOpen);
            fabIncomeBtn.setClickable(true);
            fabExpenseBtn.setClickable(true);
            fabIncomeTxt.startAnimation(fadeOpen);
            fabExpenseTxt.startAnimation(fadeOpen);
            fabIncomeTxt.setClickable(true);
            fabExpenseTxt.setClickable(true);
            isOpen = true;
        }
    }

    private void addData() {
        fabIncomeBtn.setOnClickListener(view -> incomeDataInsert());
        fabExpenseBtn.setOnClickListener(view -> expenseDataInsert());
    }

    public void incomeDataInsert() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myView = inflater.inflate(R.layout.custom_layout_for_insertdata, null);
        myDialog.setView(myView);
        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final EditText edtAmount = myView.findViewById(R.id.ammount_edt);
        final EditText edtType = myView.findViewById(R.id.type_edt);
        final EditText edtNote = myView.findViewById(R.id.note_edt);

        Button btnSave = myView.findViewById(R.id.btnSave);
        Button btnCancel = myView.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(view -> {
            String type = edtType.getText().toString().trim();
            String amount = edtAmount.getText().toString().trim();
            String note = edtNote.getText().toString().trim();

            if (TextUtils.isEmpty(type)) {
                edtType.setError("Required Field..");
                return;
            }
            if (TextUtils.isEmpty(amount)) {
                edtAmount.setError("Required Field..");
                return;
            }
            int ourAmountInt = Integer.parseInt(amount);
            if (TextUtils.isEmpty(note)) {
                edtNote.setError("Required Field..");
                return;
            }

            String id = mIncomeDatabase.push().getKey();
            String mDate = DateFormat.getDateInstance().format(new Date());
            Data data = new Data(ourAmountInt, type, note, id, mDate);

            mIncomeDatabase.child(id).setValue(data);
            Toast.makeText(getActivity(), "Data ADDED", Toast.LENGTH_SHORT).show();
            toggleFloatingButtons();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(view -> {
            toggleFloatingButtons();
            dialog.dismiss();
        });

        dialog.show();
    }

    public void expenseDataInsert() {
        AlertDialog.Builder myDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        View myView = inflater.inflate(R.layout.custom_layout_for_insertdata, null);
        myDialog.setView(myView);

        final AlertDialog dialog = myDialog.create();
        dialog.setCancelable(false);

        final EditText amount = myView.findViewById(R.id.ammount_edt);
        final EditText type = myView.findViewById(R.id.type_edt);
        final EditText note = myView.findViewById(R.id.note_edt);

        Button btnSave = myView.findViewById(R.id.btnSave);
        Button btnCancel = myView.findViewById(R.id.btnCancel);

        btnSave.setOnClickListener(view -> {
            String typeStr = type.getText().toString().trim();
            String amountStr = amount.getText().toString().trim();
            String noteStr = note.getText().toString().trim();

            if (TextUtils.isEmpty(typeStr)) {
                type.setError("Required Field..");
                return;
            }
            if (TextUtils.isEmpty(amountStr)) {
                amount.setError("Required Field..");
                return;
            }
            int ourAmountInt = Integer.parseInt(amountStr);
            if (TextUtils.isEmpty(noteStr)) {
                note.setError("Required Field..");
                return;
            }

            String id = mExpenseDatabase.push().getKey();
            String mDate = DateFormat.getDateInstance().format(new Date());
            Data data = new Data(ourAmountInt, typeStr, noteStr, id, mDate);

            mExpenseDatabase.child(id).setValue(data);
            Toast.makeText(getActivity(), "Data ADDED", Toast.LENGTH_SHORT).show();
            toggleFloatingButtons();
            dialog.dismiss();
        });

        btnCancel.setOnClickListener(view -> {
            toggleFloatingButtons();
            dialog.dismiss();
        });

        dialog.show();
    }
}
