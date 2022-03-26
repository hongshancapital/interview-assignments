//
//  ItemView.swift
//  SwiftUIDemo
//
//  Created by didi_qihang on 2022/3/13.
//

import SwiftUI

struct ItemView: View {
    var item: Item
    var body: some View {
        HStack {
            Spacer().frame(width: 15, height: 100)
            KFImage.url(URL(string: item.headURL))
                .frame(width: 60, height: 60, alignment: .center)
                .cornerRadius(8)
            VStack(alignment: .leading, spacing: 5) {
                Text(item.title)
                    .font(.title)
                    .lineLimit(1)
                    .frame(width: 200, alignment: .leading)
                    
                Text(item.subTitle)
                    .font(.subheadline)
                    .lineLimit(2)
                    .frame(width: 200, alignment: .leading)
            }
            Image(systemName: "heart.fill")
                .foregroundColor(.red)
                .frame(width: 50, height: 50)
        }
        .background(Color.white)
        .cornerRadius(8)
    }
    
}

struct ItemView_Previews: PreviewProvider {
    static var previews: some View {
        let item = Item(
            headURL: "", title: "", subTitle: ""
        )
        ItemView(item: item)
    }
}
