//
//  ContentView.swift
//  QA engineer's toolbox
//
//  Created by Boxiang Yu - Ciic on 2021/1/5.
//

import SwiftUI

struct ContentView: View {
//    var model = ToolboxListModel(results: [ToolboxModel(id: 3, name: "Tom", tools: "E2E"), ToolboxModel(id: 1, name: "Jerry", tools: "Jest")])
    var model = ToolboxListModel()
  
    var body: some View {
        VStack{
            ToolboxList().environmentObject(model)
            Form().environmentObject(model)
        }.onAppear(perform: {
            model.loadData()
        })
    }
}

struct ContentView_Previews: PreviewProvider {
    static var previews: some View {
        ContentView()
    }
}
