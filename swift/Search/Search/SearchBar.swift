//
//  SearchBar.swift
//  Search
//
//  Created by chx on 2021/4/21.
//

import SwiftUI

struct SearchBar: View {
    @Binding var searchName : String
    var body: some View {
        HStack {
            TextField.init("Search", text: $searchName) { (change) in
                print("change \(self.searchName)")
            } onCommit: {
                UIApplication.shared.windows.first { $0.isKeyWindow }?.endEditing(true)

            }.padding()
            Button(action: {
                self.searchName = ""

            }) {
                Image(systemName: "xmark.circle.fill")
                    .foregroundColor(.secondary)
                    .opacity(searchName == "" ? 0 : 1)
            }.padding()
 
        }
    }
}

//struct SearchBar_Previews: PreviewProvider {
////    @Binding var searchName : String
//    static var previews: some View {
////        SearchBar(searchName: searchName)
//    }
//}
