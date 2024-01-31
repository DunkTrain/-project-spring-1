function delete_task(task_id) {
    let url = "/" + task_id;
    $.ajax({
        url: url,
        type: 'DELETE',
        success: function () {
            window.location.reload();
        }
    });
}

function edit_task(task_id) {
    let identifier_delete = "#delete_" + task_id;
    $(identifier_delete).remove();

    // replace "edit" button with "save"
    let identifier_edit = "#edit_" + task_id;
    let save_tag = "<button id='save_" + task_id + "' onclick='update_task(" + task_id + ")'>Save</button>";
    $(identifier_edit).html(save_tag);

    let current_tr_element = $(identifier_edit).parent().parent();
    let children = current_tr_element.children();
    let td_description = children[1];
    td_description.innerHTML = "<input id='input_description_" + task_id + "' type='text' value='" + td_description.innerHTML + "'>";

    let td_status = children[2];
    let status_id = "#status_" + task_id;
    let status_current_value = td_status.innerHTML.trim();
    td_status.innerHTML = getDropdownStatusHTML(task_id, status_current_value);
}

function getDropdownStatusHTML(task_id, selected_status) {
    return "<select id='input_status_" + task_id + "'>"
        + "<option value='IN_PROGRESS' " + (selected_status === 'IN_PROGRESS' ? 'selected' : '') + ">IN_PROGRESS</option>"
        + "<option value='DONE' " + (selected_status === 'DONE' ? 'selected' : '') + ">DONE</option>"
        + "<option value='PAUSED' " + (selected_status === 'PAUSED' ? 'selected' : '') + ">PAUSED</option>"
        + "</select>";
}

function update_task(task_id) {
    let url = "/" + task_id;
    let value_description = $("#input_description_" + task_id).val();
    let value_status = $("#input_status_" + task_id).val();

    $.ajax({
        url: url,
        type: 'POST',
        dataType: 'json',
        contentType: 'application/json;charset=UTF-8',
        async: false,
        data: JSON.stringify({"description": value_description, "status": value_status}),
        success: function () {
            // Обновляем значение статуса в HTML
            $("#status_" + task_id).text(value_status);
        }
    });

    setTimeout(() => {
        document.location.reload();
    }, 300);
}

function add_task() {
    let value_description = $("#description_new").val();
    let value_status = $("#status_new").val();

    $.ajax({
        url: "/",
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
