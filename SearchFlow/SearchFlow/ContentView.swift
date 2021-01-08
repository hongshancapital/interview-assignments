//
//  ContentView.swift
//  SearchFlow
//
//  Created by evan on 2021/1/6.
//

import SwiftUI

struct ContentView: View {

    var body: some View {
        NavigationView {
            VStack {
                SearchView()
                SearchResultView()
                Spacer()
            }
            .navigationTitle("Search")
            .navigationBarColor(Color.offWhite)
            .background(Color.offWhite)
            .edgesIgnoringSafeArea(.bottom)
        }
        .navigationViewStyle(StackNavigationViewStyle())
        .background(Color.offWhite)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
