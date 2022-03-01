//
//  ContentView.swift
//  ListDemo
//
//  Created by Chr1s on 2022/2/21.
//

import SwiftUI

struct ContentView: View {
    
    let dataService = ApiService()
    var body: some View {
       ListView(dataService: dataService)
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
