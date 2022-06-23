package com.example.bb.donor.adapters

import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.example.bb.R
import com.example.bb.database.Charity
import com.squareup.picasso.Picasso

@BindingAdapter("charityName")
fun TextView.setCharityName(item: Charity?)
{
    item?.let {
        text = item.name
    }
}
