//
//  ListPage.swift
//  ListProject
//
//  Created by shencong on 2022/6/8.
//

import SwiftUI

struct ListPage: View {
    var allLists: [ItemModel] = allListData

    var body: some View {
        NavigationView {
            SimpleList(allLists: allLists)
                .navigationBarTitle("App", displayMode: .large)
                .environmentObject(ListData())
        }
    }
}


struct ListPage_Previews: PreviewProvider {
    static var previews: some View {
        ListPage()
    }
}
