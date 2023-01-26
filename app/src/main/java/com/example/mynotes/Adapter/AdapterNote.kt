package com.example.mynotes.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.data.Note

/*
class AdapterNote (private val items: List<Note>,
                   private val onItemClick: (createNote: Note) -> Unit
) :
    RecyclerView.Adapter<AdapterNote.FriendViewHolder>() {

    class FriendViewHolder(val viewDataBinding: ItemNoteBinding) :
        RecyclerView.ViewHolder(viewDataBinding.root) {
        fun bind(friend: Note?) {
            viewDataBinding.createNote = Note
            viewDataBinding.executePendingBindings()

//            val bitmapPhotoResult =
//                BitmapConverter.decodeToBitmap(itemView.context, Note!!.photo)
//
//            viewDataBinding.ivProfile.setImageBitmap(bitmapPhotoResult)

        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FriendViewHolder {

        val binding: ItemNoteBinding = DataBindingUtil.inflate(
            LayoutInflater.from(parent.context),
            R.layout.fragment_home,
            parent,
            false
        )

        return FriendViewHolder(binding)

    }

    override fun onBindViewHolder(holder: FriendViewHolder, position: Int) {
        holder.bind(items[position])
        holder.itemView.setOnClickListener { onItemClick(items[position]) }
    }

    override fun getItemCount(): Int {
        return items.size
    })
}*/
