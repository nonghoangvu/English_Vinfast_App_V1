const logout = () => {
    swal({
        title: "Are you sure?",
        text: "Do you want to log out?",
        icon: "warning",
        buttons: true,
        dangerMode: true,
    })
        .then((willLogout) => {
            if (willLogout) {
                window.location.href='/logout'
            }
        });
}