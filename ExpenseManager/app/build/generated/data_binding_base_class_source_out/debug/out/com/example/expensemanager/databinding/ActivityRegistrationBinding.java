// Generated by view binder compiler. Do not edit!
package com.example.expensemanager.databinding;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.viewbinding.ViewBinding;
import androidx.viewbinding.ViewBindings;
import com.example.expensemanager.R;
import java.lang.NullPointerException;
import java.lang.Override;
import java.lang.String;

public final class ActivityRegistrationBinding implements ViewBinding {
  @NonNull
  private final LinearLayout rootView;

  @NonNull
  public final Button btnReg;

  @NonNull
  public final EditText emailReg;

  @NonNull
  public final EditText passwordReg;

  @NonNull
  public final TextView signinHere;

  private ActivityRegistrationBinding(@NonNull LinearLayout rootView, @NonNull Button btnReg,
      @NonNull EditText emailReg, @NonNull EditText passwordReg, @NonNull TextView signinHere) {
    this.rootView = rootView;
    this.btnReg = btnReg;
    this.emailReg = emailReg;
    this.passwordReg = passwordReg;
    this.signinHere = signinHere;
  }

  @Override
  @NonNull
  public LinearLayout getRoot() {
    return rootView;
  }

  @NonNull
  public static ActivityRegistrationBinding inflate(@NonNull LayoutInflater inflater) {
    return inflate(inflater, null, false);
  }

  @NonNull
  public static ActivityRegistrationBinding inflate(@NonNull LayoutInflater inflater,
      @Nullable ViewGroup parent, boolean attachToParent) {
    View root = inflater.inflate(R.layout.activity_registration, parent, false);
    if (attachToParent) {
      parent.addView(root);
    }
    return bind(root);
  }

  @NonNull
  public static ActivityRegistrationBinding bind(@NonNull View rootView) {
    // The body of this method is generated in a way you would not otherwise write.
    // This is done to optimize the compiled bytecode for size and performance.
    int id;
    missingId: {
      id = R.id.btn_reg;
      Button btnReg = ViewBindings.findChildViewById(rootView, id);
      if (btnReg == null) {
        break missingId;
      }

      id = R.id.email_reg;
      EditText emailReg = ViewBindings.findChildViewById(rootView, id);
      if (emailReg == null) {
        break missingId;
      }

      id = R.id.password_reg;
      EditText passwordReg = ViewBindings.findChildViewById(rootView, id);
      if (passwordReg == null) {
        break missingId;
      }

      id = R.id.signin_here;
      TextView signinHere = ViewBindings.findChildViewById(rootView, id);
      if (signinHere == null) {
        break missingId;
      }

      return new ActivityRegistrationBinding((LinearLayout) rootView, btnReg, emailReg, passwordReg,
          signinHere);
    }
    String missingId = rootView.getResources().getResourceName(id);
    throw new NullPointerException("Missing required view with ID: ".concat(missingId));
  }
}
