//
//  ContentView.swift
//  Assignment
//
//  Created by Tpphha on 2021/2/22.
//

import SwiftUI

struct ContentView: View {
    
    @EnvironmentObject var appState: AppState
    @State private var canAnimate = false
    
    var body: some View {
        NavigationView {
            ZStack {
                if appState.currentUser != nil {
                    ProfileView()
                        .transition(.slide)
                        .animation(canAnimate ? .easeInOut : nil)
                        
                } else {
                    LoginView()
                        .transition(.slide)
                        .animation(canAnimate ? .easeInOut : nil)
                }
            }
        }
        .onAppear(perform: {
            DispatchQueue.main.asyncAfter(deadline: .now() + 0.25) {
                canAnimate = true
            }
        })
    }
    
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
