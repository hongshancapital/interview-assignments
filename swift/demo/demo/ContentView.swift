//
//  ContentView.swift
//  demo
//
//  Created by 张帅 on 2023/4/8.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationView {
            FLUserListView().environmentObject(FLUserViewModel())
        }
        .navigationTitle("app")
        .onAppear {
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
