//
//  ContentView.swift
//  SwiftDemo
//
//  Created by liuyang on 2023/3/5.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationView{
            AppTableView().navigationTitle("App")
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}

