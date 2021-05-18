import SwiftUI

//MARK:-
//MARK:- Color
let lightGreyColor = Color(red: 214.0/255.0, green: 214.0/255.0, blue: 214.0/255.0, opacity: 1.0)
let greenColor = Color(red: 120.0/255.0, green: 184.0/255.0, blue: 88.0/255.0, opacity: 1.0)
let lightestGreyColor = Color(red: 247.0/255.0, green: 247.0/255.0, blue: 247.0/255.0, opacity: 1.0)

let Width = UIScreen.main.bounds.width
let Heigh = UIScreen.main.bounds.height

struct ContentView: View {
    @EnvironmentObject var settings: UserSettings

    var body: some View {
        /// Set the initialization state
        let userSettings = UserSettings()

        if UserDefaults.standard.bool(forKey: "loggedIn") {
            userSettings.loggedIn = true
            return AnyView(PersonView())
        } else {
            userSettings.loggedIn = false
            return AnyView(LoginAndCreateAccountView())
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
