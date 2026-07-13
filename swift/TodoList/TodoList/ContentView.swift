//
//  ContentView.swift
//  TodoList
//
//  Created by Huanrong on 11/2/21.
//

import SwiftUI

struct ContentView: View {
    var body: some View {
        TodlListView()
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView().environmentObject(ModelData())
    }
}
