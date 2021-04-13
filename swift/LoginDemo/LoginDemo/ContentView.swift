//
//  ContentView.swift
//  LoginDemo
//
//  Created by MC on 2021/4/9.
//

import SwiftUI

struct ContentView: View {

    var body: some View {
        NavigationView {
            NavigationLink(
                destination: LoginView()) {
                Text("Login")
            }
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
