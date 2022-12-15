package com.ebinumer.loginregisterwithroomdb.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.ebinumer.loginregisterwithroomdb.R
import com.ebinumer.loginregisterwithroomdb.data.Database.RegisterEntity
import com.ebinumer.loginregisterwithroomdb.databinding.ListItemBinding

class MyRecycleViewAdapter (private val usersList :List<RegisterEntity>): RecyclerView.Adapter<MyviewHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyviewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return MyviewHolder(DataBindingUtil.inflate(layoutInflater, R.layout.list_item,parent,false))
    }

    override fun getItemCount(): Int {
        return usersList.size
    }

    override fun onBindViewHolder(holder: MyviewHolder, position: Int) {
        usersList[position]?.let {
            holder.bind(it)
        }


    }


}

class MyviewHolder(private val binding :ListItemBinding ):RecyclerView.ViewHolder(binding.root){

    fun bind(user : RegisterEntity){
        binding.apply {
//            data = user
            binding.apply {
                user.apply {
                    FirstNameTextView.text = Name
                    emailTextView.text = "Email : $email"
                    userTextField.text = "Mob : $mobile"
                }

            }
        }
    }

}