package enghack.motivateme.Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import enghack.motivateme.R;
import twitter4j.Status;

/**
 * Created by rowandempster on 3/28/17.
 */

public class CategoryQuoteListAdapter extends RecyclerView.Adapter<CategoryQuoteListAdapter.QuoteViewHolder> {
    List<Status> _quotes;

    public CategoryQuoteListAdapter(List<Status> quotes) {
        _quotes = quotes;
    }

    @Override
    public QuoteViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new QuoteViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.quote_view_holder, parent, false));
    }

    @Override
    public void onBindViewHolder(QuoteViewHolder holder, int position) {
        holder.setQuote(_quotes.get(position));
    }

    @Override
    public int getItemCount() {
        return _quotes.size();
    }

    public class QuoteViewHolder extends RecyclerView.ViewHolder{
        TextView _quoteTextView;
        public QuoteViewHolder(View itemView) {
            super(itemView);
            _quoteTextView = (TextView) itemView.findViewById(R.id.quote_view_holder_text_view);
        }

        public void setQuote(Status status) {
            _quoteTextView.setText(status.getText());
        }
    }
}
