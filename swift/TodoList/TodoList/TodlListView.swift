//
//  TodlList.swift
//  TodoList
//
//  Created by Huanrong on 11/2/21.
//

import SwiftUI

struct TodlListView: View {
    @EnvironmentObject var modelData: ModelData
    var body: some View {
        NavigationView {
            ZStack {
                List {
                    ForEach(modelData.sections) { section in
                        Section(header: Text(section.sectionName)) {
                            ForEach(section.items) { item in
                                TaskitemCell(itemModel: item, modelData: self.modelData)
                            }
                        }
                    }
                }
                VStack {
                    Spacer()
                    AddTaskView(modelData: self.modelData).frame(width: UIScreen.main.bounds.size.width, height: 50, alignment: .bottom).background(Color.white).ignoresSafeArea(.keyboard, edges: .bottom)
                }
            }.navigationTitle(Text("List"))
        }
    }
    
    func delete(index: IndexSet) {
        
    }
}

struct TodlListView_Previews: PreviewProvider {
    static var previews: some View {
        TodlListView().environmentObject(ModelData())
    }
}
