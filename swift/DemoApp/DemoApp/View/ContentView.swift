//
//  ContentView.swift
//  DemoApp
//
//  Created by liang on 2022/5/18.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        AppList(dataMgr: DataManager(dataProvider: DataProvider()))
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
