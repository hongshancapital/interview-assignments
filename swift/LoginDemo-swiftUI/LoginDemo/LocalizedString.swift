import Foundation

enum LocalizedString {
    enum Root {
        static let logOut = NSLocalizedString(
            "Root.LogOutTitle",
            value: "Login Out",
            comment: "Log out from root view to use"
        )
    }

    enum Login {
        static let createAccount = NSLocalizedString(
            "Login.CreateAccount",
            value: "Create Account",
            comment: "Use when switching between login and account creation modes"
        )

        static let login = NSLocalizedString(
            "Login.Login",
            value: "Login",
            comment: "The title display on login button"
        )

        static let userName = NSLocalizedString(
            "Login.UserName",
            value: "Name",
            comment: "Placeholder for textFile display of user name"
        )

        static let passWord = NSLocalizedString(
            "Login.PassWord",
            value: "PassWord",
            comment: "Placeholder for textFile display of pass word"
        )

        static let RepeatPassWord = NSLocalizedString(
            "Login.RepeatPassWord",
            value: "Repeat PassWord",
            comment: "Placeholder for textFile display of repeat pass word"
        )

        static let loginError = NSLocalizedString(
            "Login.LoginError",
            value: "Wrong user name or password",
            comment: "Prompt for login failure"
        )

        static let passWordError  = NSLocalizedString(
            "Login.LoginError",
            value: "Wrong password, please check again",
            comment: "Password verification failed"
        )
        
        static let createAccountError  = NSLocalizedString(
            "Login.LoginError",
            value: "Failed to create account, please change user name",
            comment: "Failed to create account"
        )
    }
}
