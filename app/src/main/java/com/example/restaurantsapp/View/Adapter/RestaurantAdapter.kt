package com.example.restaurantsapp.View.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.restaurantsapp.R
import com.example.restaurantsapp.databinding.RestaurantItemBinding
import com.example.restaurantsapp.databinding.ReviewItemBinding
import com.example.restaurantsapp.model.ReviewModel.ReviewDomain.ReviewDomain
import com.example.restaurantsapp.model.domain.RestaurantDomain
import com.example.restaurantsapp.utils.ViewType

class RestaurantAdapter(
    private val itemSet: MutableList<ViewType> = mutableListOf(),
    private val onItemClick: (RestaurantDomain) -> Unit
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {


    fun updateItems(newItems: MutableList<ViewType>) {
        if (itemSet != newItems) {
            itemSet.clear()
            itemSet.addAll(newItems)
            notifyDataSetChanged()
        }
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return if (viewType == 0) {
            RestaurantViewHolder(
                RestaurantItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        } else {
            ReviewViewHolder(
                ReviewItemBinding.inflate(
                    LayoutInflater.from(parent.context),
                    parent,
                    false
                )
            )
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (itemSet[position]) {

            is ViewType.RestuarantList -> 0
            is ViewType.ReviewList -> 1
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (val item = itemSet[position]) {
            is ViewType.RestuarantList -> (holder as RestaurantViewHolder).bind(
                item.restaurantList,
                onItemClick
            )
            is ViewType.ReviewList -> (holder as ReviewViewHolder).bind(
                item.reviewList

            )
        }
    }

    override fun getItemCount(): Int = itemSet.size

}


class RestaurantViewHolder(
    private val binding: RestaurantItemBinding
) :
    RecyclerView.ViewHolder(binding.root) {

    fun bind(item: RestaurantDomain, onItemClick: (RestaurantDomain) -> Unit) {

        binding.tvRestaurantName.text = item.name
        binding.tvPhoneNumber.text = item.phone
        binding.tvDistance.text = item.distance.toString()
        binding.tvPrice.text = item.price


        Glide
            .with(binding.root)
            .load(item.image)
            .centerCrop()
            .placeholder(R.drawable.baseline_crop_free_24)
            .error(R.drawable.baseline_broken_image_24)
            .into(binding.ivRestaurantImage)


        itemView.setOnClickListener {
            onItemClick(item)
        }
    }

}

class ReviewViewHolder(
    private val binding: ReviewItemBinding,

    ) : RecyclerView.ViewHolder(binding.root) {

    fun bind(item: ReviewDomain) {


        binding.tvUserName.text = item.userName
        binding.rbRating.rating = item.rating.toFloat()
        binding.tvText.text = item.text
        binding.tvTimeCreated.text = item.timeCreated
        binding.tvProfileUrl.text = item.userImgUrl



        Glide
            .with(binding.root)
            .load(item.userImgUrl)
            .centerCrop()
            .placeholder(R.drawable.baseline_crop_free_24)
            .error(R.drawable.baseline_broken_image_24)
            .into(binding.ivUserImage)

    }
}

