package `in`.demesne.myaccount.features.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import `in`.demesne.myaccount.data.models.account.WebAuthnData
import `in`.demesne.myaccount.databinding.ItemAccountDataBinding
import `in`.demesne.myaccount.databinding.ItemAccountHeaderBinding
import android.view.View

class MyAccountWebAuthnListAdapter(
    private val onAddClick: () -> Unit,
    private val onDeleteClick: (id: String) -> Unit
) : RecyclerView.Adapter<MyAccountWebAuthnListAdapter.AccountViewHolder>() {

    companion object {
        private const val VIEW_TYPE_HEADER = 0
        private const val VIEW_TYPE_ITEM = 1
    }

    private var items = listOf<WebAuthnData>()

    fun updateData(newItems: List<WebAuthnData>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = items.size + 1 // +1 for header

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) VIEW_TYPE_HEADER else VIEW_TYPE_ITEM
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        return if (viewType == VIEW_TYPE_HEADER) {
            val binding = ItemAccountHeaderBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AccountViewHolder.Header(binding, onAddClick)
        } else {
            val binding = ItemAccountDataBinding.inflate(LayoutInflater.from(parent.context), parent, false)
            AccountViewHolder.Item(binding, onDeleteClick)
        }
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_TYPE_HEADER && holder is AccountViewHolder.Header) {
            holder.bind()
        } else if (holder is AccountViewHolder.Item) {
            holder.bind(items[position - 1])
        }
    }

    sealed class AccountViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        class Header(
            private val binding: ItemAccountHeaderBinding,
            private val onAddClick: () -> Unit
        ) : AccountViewHolder(binding.root) {
            fun bind() {
                binding.tvSubheader.text = "Passkey and Security Key"
                binding.btnAdd.setOnClickListener { onAddClick() }
            }
        }
        class Item(
            private val binding: ItemAccountDataBinding,
            private val onDeleteClick: (id: String) -> Unit
        ) : AccountViewHolder(binding.root) {
            fun bind(item: WebAuthnData) {
                binding.tvTitle.text = item.name
                binding.tvSubtitle.text = item.credentialId
                binding.tvDescription.text = item.id
                binding.btnDelete.setOnClickListener { onDeleteClick(item.id) }
            }
        }
    }
}