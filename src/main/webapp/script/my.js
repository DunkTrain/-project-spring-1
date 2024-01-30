function add_task() {
    let value_description = $("#description_new").val();
    let value_status = $("#status_new").val();

    $.ajax({
        url: getBaseUrl(),
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        async: false,
        data: JSON.stringify({"description": value_description, "status": value_status})
    });

    setTimeout(() => {
        document.location.reload();
    }, 300);
}

function edit_task(task_id) {
    let id_delete = "#delete_" + task_id;
    $(id_delete).remove();

    let id_edit = "#edit_" + task_id;
    let save_tag = "<button class='button-32' role='button' id='save_" + task_id + "'>Save</button>";
    $(id_edit).html(save_tag);
    let prop_save_tag = "update_task(" + task_id + ")";
    $(id_edit).attr("onclick", prop_save_tag);

    let current_tr_element = $(id_edit).parent().parent();
    let children = current_tr_element.children();
    let td_description = children[1];
    td_description.innerHTML = "<input id='input_description_" + task_id + "' type='text' value='" + td_description.innerHTML + "'>";

    let td_status = children[2];
    let status_id = "#select_status_" + task_id;
    let status_current_value = td_status.innerHTML;
    td_status.innerHTML = getDropdownStatusHtml(task_id);
    $(status_id).val(status_current_value).change();
}

function update_task(task_id) {
    let url = getBaseUrl() + task_id;

    let value_description = $("#input_description_" + task_id).val();
    let value_status = $("#select_status_" + task_id).val();

    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        async: false,
        data: JSON.stringify({"description": value_description, "status": value_status})
    });

    setTimeout(() => {
        document.location.reload();
    }, 300);
}

function delete_task(task_id) {
    let url = getBaseUrl() + task_id;
    $.ajax({
        url: url,
        type: 'DELETE',
        success: function () {
            window.location.reload();
        }
    });
}

function getDropdownStatusHtml(task_id) {
    let status_id = "select_status_" + task_id;
    return "<label for='status'></label>"
        + "<select id=" + status_id + " name='status'>"
        + "<option value='IN_PROGRESS'>IN_PROGRESS</option>"
        + "<option value='DONE'>DONE</option>"
        + "<option value='PAUSED'>PAUSED</option>"
        + "</select>";
}

function getBaseUrl() {
    let current_path = window.location.href;
    let end_position = current_path.indexOf('?');
    return current_path.substring(0, end_position);
}