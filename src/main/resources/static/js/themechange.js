document.addEventListener('DOMContentLoaded', () => {
    let currentTheme = getTheme();
    changeTheme(currentTheme);

    function changeTheme(theme) {
        document.querySelector('html').classList.add(theme);

        const changeThemeButton = document.getElementById("theme-btn");
        let themeText = document.querySelector('#theme-text');
        let themeIcon = document.querySelector('#theme-icon');

        if (changeThemeButton) {
            changeThemeButton.addEventListener("click", function () {
                document.querySelector('html').classList.remove(currentTheme);
                if (currentTheme === "dark") {
                    currentTheme = "light";
                    if (themeText) themeText.innerHTML = "Dark";
                    if (themeIcon) {
                        themeIcon.classList.remove("fa-sun");
                        themeIcon.classList.add("fa-moon");
                    }
                } else {
                    currentTheme = "dark";
                    if (themeText) themeText.innerHTML = "Light";
                    if (themeIcon) {
                        themeIcon.classList.remove("fa-moon");
                        themeIcon.classList.add("fa-sun");
                    }
                }
                setTheme(currentTheme);
                document.querySelector('html').classList.add(currentTheme);
            });
        }
    }

    // Get theme value from local storage
    function getTheme() {
        let theme = localStorage.getItem('theme');
        return theme ? theme : 'light';
    }

    // Set theme value in local storage
    function setTheme(theme) {
        localStorage.setItem("theme", theme);
    }
});