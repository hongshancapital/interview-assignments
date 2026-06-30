//
//  BaseRow.swift
//  SwiftUIDemo
//
//  Created by HBC on 2022/2/18.
//

import SwiftUI

struct BaseRow: View {
    var listItem : ListItem
    
    var body: some View {
        HStack {
            Image(listItem.image)
                .resizable()
                .frame(width: 40, height: 40)
            Text(listItem.name)
                .padding()
        }
    }
}

struct BaseRow_Previews: PreviewProvider {
    static var previews: some View {
        BaseRow(listItem: ListItem(name: "666", image: "logo_00096"))
    }
}
