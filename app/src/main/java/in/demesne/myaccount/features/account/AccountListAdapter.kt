package `in`.demesne.myaccount.features.account

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import `in`.demesne.myaccount.data.models.account.webauthn.WebAuthnData
import `in`.demesne.myaccount.databinding.ItemAccountDataBinding

class AccountListAdapter : RecyclerView.Adapter<AccountListAdapter.AccountViewHolder>() {

    private var items = listOf<WebAuthnData>()

    fun updateData(newItems: List<WebAuthnData>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AccountViewHolder {
        val binding = ItemAccountDataBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return AccountViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AccountViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    class AccountViewHolder(private val binding: ItemAccountDataBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: WebAuthnData) {
            binding.tvTitle.text = item.name
            binding.tvSubtitle.text = item.credentialId
            binding.tvDescription.text = item.id
        }
    }
}