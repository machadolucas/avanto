function disable_enter_key(evt) {
	var ret = true;

	var evt = evt || event;
	if ((evt.keyCode || event.which || event.charCode || 0) == 13) {
		if (evt.stopPropagation) {
			evt.stopPropagation();
			evt.preventDefault();
		}
		ret = false;
	}

	return ret;

}

function clear_datatable_selection() {

	var inputSelection = $("input[id$='dataTable_selection']");
	if (inputSelection == null) {
		return;
	}
	inputSelection.attr('value', null);

}

function disable_enter_key_in_all_inputs() {

	var forms = document.getElementsByTagName("form");
	if (forms == null || forms.length == 0) {
		return;
	}

	for ( var idx = 0; idx < forms.length; idx++) {

		var form = forms[idx];

		if (form.addEventListener) {
			form.addEventListener('keypress', disable_enter_key, false);
		} else if (form.attachEvent) {
			form.attachEvent('onkeypress', disable_enter_key);
		}

	}
}

function on_body_load() {

	disable_enter_key_in_all_inputs();
	clear_datatable_selection();

}

function handleDetailsPopupRequest(xhr, status, args) {
	if (args.canContinue) {
		detailsDialogWid.show();
	} else {
		detailsDialogWid.hide();
	}
}

function handleDeletePopupRequest(xhr, status, args) {
	if (args.canContinue) {
		confirmDeleteDialogWid.show();
	} else {
		confirmDeleteDialogWid.hide();
	}
}