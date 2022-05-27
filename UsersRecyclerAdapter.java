package com.androiddatabase.loginregister.adapters;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.widget.AppCompatTextView;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.androiddatabase.loginregister.UsersListActivity;
import com.androiddatabase.loginregister.UsersListActivity;
import com.androiddatabase.loginregister.model.User;
import com.androiddatabase.loginregister.sql.DatabaseHelper;

import java.util.List;

import androiddatabase.loginregister.R;


public class UsersRecyclerAdapter extends RecyclerView.Adapter<UsersRecyclerAdapter.UserViewHolder> {

    private List<User> listUsers;
    private DatabaseHelper databaseHelper;
    private Context context;


    public UsersRecyclerAdapter(Context context, List<User> listUsers) {
        this.context = context;
        this.listUsers = listUsers;
    }

    @Override
    public UserViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        // inflating recycler item view
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_user_recycler, parent, false);

        return new UserViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(UserViewHolder holder, int position) {
        holder.textViewName.setText(listUsers.get(position).getName());
        holder.textViewEmail.setText(listUsers.get(position).getEmail());
        holder.textViewPassword.setText(listUsers.get(position).getPassword());
    }

    @Override
    public int getItemCount() {
        Log.v(UsersRecyclerAdapter.class.getSimpleName(),""+listUsers.size());
        return listUsers.size();
    }


    /**
     * ViewHolder class
     */
    public class UserViewHolder extends RecyclerView.ViewHolder {

        public AppCompatTextView textViewName;
        public AppCompatTextView textViewEmail;
        public AppCompatTextView textViewPassword;

        public UserViewHolder(View view) {
            super(view);
            view.setOnClickListener(this::onClick);
            textViewName = (AppCompatTextView) view.findViewById(R.id.textViewName);
            textViewEmail = (AppCompatTextView) view.findViewById(R.id.textViewEmail);
            textViewPassword = (AppCompatTextView) view.findViewById(R.id.textViewPassword);
            databaseHelper = new DatabaseHelper(context);
        }

        public void onClick(View view){
            AlertDialog.Builder builder1 = new AlertDialog.Builder(context);
            builder1.setMessage("Are you sure you want to delete user?");
            builder1.setCancelable(true);
            builder1.setPositiveButton(
                    "Yes",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            databaseHelper.deleteUser(listUsers.get(getAdapterPosition()));
                            Toast toast = Toast.makeText(context,"User Deleted",Toast.LENGTH_LONG);
                            toast.show();
                            Intent intent = new Intent(context, UsersListActivity.class);
                            context.startActivity(intent);
                        }
                    });
            builder1.setNegativeButton(
                    "No",
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.cancel();
                        }
                    });
            AlertDialog alert11 = builder1.create();
            alert11.show();

        }
    }


}
