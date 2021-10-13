//
//  SearchListView.swift
//  SearchBarDemo
//
//  Created by Jaydon Qin on 2021/9/28.
//

import SwiftUI

struct SearchListView: View {
    @Binding var searchTerm : String
    
    var body: some View {
        if let arr = searchModels {
            List{
                ForEach(arr.indices){ i in
                    if let sectionModel = searchModels?[i] {
                        if self.searchTerm.isEmpty || self.searchTerm == "" ||  sectionModel.searchId.localizedCaseInsensitiveContains(self.searchTerm){
                            Section (header:  Text(sectionModel.title), content: {
                                if let rowArr = sectionModel.content{
                                    ForEach (rowArr.indices){ j in
                                        SearchCell(searchModel: rowArr[j])
                                    }
                                }
                            })
                        }else if i == arr.count - 1{
                            NoDataCell()
                        }
                    }
                }
            }
            .listStyle(GroupedListStyle())
        }
        
    }
}

struct SearchListView_Previews: PreviewProvider {
    static var previews: some View {
        SearchListView(searchTerm: .constant("hello"))
    }
}
