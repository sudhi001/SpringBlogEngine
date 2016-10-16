var handleClickChangeUserIconBtn = function() {
   resetChangeUserIconDlg();
   $("#addUserIconDlg").modal("show");
};

$('#addUserIconDlg').on('hidden.bs.modal', function () {
   resetChangeUserIconDlg();
});

var resetChangeUserIconDlg = function() {
   
   $("#addUserIconDlg #addUserIconForm")[0].reset();
};

