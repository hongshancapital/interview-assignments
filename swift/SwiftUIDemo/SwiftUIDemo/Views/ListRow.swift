//
//  ListRow.swift
//  SwiftUIDemo
//
//  Created by Machao on 2022/4/15.
//

import SwiftUI

struct ListRow: View {
    @State var linkModel: LinkModel
    var body: some View {
        HStack(spacing: 5) {
            NetworkImage(imageURLString: linkModel.imageURLString)
                .frame(width: 40, height: 40, alignment: .center)
            VStack(alignment: .leading) {
                Text(linkModel.title)
                    .font(.system(size: 15))
                    .bold()
                    .lineLimit(1)
                Text(linkModel.body)
                    .font(.system(size: 12))
                    .lineLimit(2)
            }
            Spacer()
            Image(systemName: "heart\(linkModel.favorite ? ".fill" : "")")
                .foregroundColor(linkModel.favorite ? .red : .black)
                .scaleEffect(linkModel.favorite ? 1.1 : 1.0)
                .onTapGesture {
                    withAnimation {
                        linkModel.favorite.toggle()
                    }
                }
        }
        .frame(maxWidth: .infinity)
        .padding(10)
        .background(Color.white)
    }
}

struct ListRow_Previews: PreviewProvider {
    static var previews: some View {
        ListRow(linkModel: LinkModel(title: "aaa", body: "bbb", imageURLString: "http://www"))
    }
}
