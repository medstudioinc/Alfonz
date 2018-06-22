package org.alfonz.samples.alfonzadapter;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;

import org.alfonz.samples.R;
import org.alfonz.samples.alfonzarch.BaseFragment;
import org.alfonz.samples.databinding.FragmentAdapterSampleListBinding;

public class AdapterSampleListSimpleFragment extends BaseFragment<AdapterSampleViewModel, FragmentAdapterSampleListBinding> implements AdapterSampleView {
	private MessageListSimpleAdapter mAdapter;

	public static AdapterSampleListSimpleFragment newInstance() {
		return new AdapterSampleListSimpleFragment();
	}

	@Override
	public AdapterSampleViewModel setupViewModel() {
		AdapterSampleViewModel viewModel = ViewModelProviders.of(this).get(AdapterSampleViewModel.class);
		getLifecycle().addObserver(viewModel);
		return viewModel;
	}

	@Override
	public FragmentAdapterSampleListBinding inflateBindingLayout(@NonNull LayoutInflater inflater) {
		return FragmentAdapterSampleListBinding.inflate(inflater);
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		getBinding().executePendingBindings(); // helps to reload recycler scroll position after orientation change
		setupAdapter();
	}

	@Override
	public void onItemClick(String message) {
		String newMessage = getViewModel().addMessage();
		showSnackbar(getString(R.string.adapter_sample_hello, newMessage));
	}

	@Override
	public boolean onItemLongClick(String message) {
		getViewModel().removeMessage(message);
		showToast(getString(R.string.adapter_sample_goodbye, message));
		return true;
	}

	private void setupAdapter() {
		if (mAdapter == null) {
			mAdapter = new MessageListSimpleAdapter(this, getViewModel());
			getBinding().adapterSampleListRecycler.setAdapter(mAdapter);
		}
	}
}
