//
//  ContentView.swift
//  SwiftUIHomeWork
//
//  Created by quanwei chen on 2022/9/4.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        NavigationView {
            ZStack {
                AppListView()
                    .background(Color.init(white: 0.95))

            }
            .navigationBarTitle("刷新demo").background(Color.init(white: 0.95))
        }
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
