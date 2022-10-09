//
//  ContentView.swift
//  DemoApp
//
//  Created by Gao on 2022/7/9.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
       AppList()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView().environmentObject(ModelData())
    }
}
