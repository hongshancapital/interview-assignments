//
//  DataCell.swift
//  AppList
//
//  Created by 王宁 on 2022/4/1.
//

import SwiftUI
struct DataCell: View {
    @Binding var model: DataModel
    var action: ((DataModel) -> Void)?
    
    var body: some View {
        HStack{
            ImageWithURL(model.artworkUrl60)
                .frame(width: 60, height: 60)
                .padding(.trailing, 10)
            VStack(alignment: .leading, spacing: 5) {
                Text(model.trackCensoredName)
                    .font(.system(size: 15).bold())
                    .lineLimit(1)
                    .frame(maxWidth: .infinity, alignment: .leading)
                Text(model.description)
                    .font(.system(size: 12))
                    .lineLimit(2)
                    .frame(maxWidth: .infinity, alignment: .leading)
            }
            .frame(maxWidth: .infinity, alignment: .center)
            Button {
                model.like.toggle()
                if let act = action{
                    act(model)
                }
            } label: {
                ZStack {
                    Image(systemName: "heart")
                        .imageScale(.medium)
                        .scaleEffect(1.2)
                        .foregroundColor(.gray)
                        .opacity(model.like ? 0: 1)
                        .animation(.easeOut, value: model.like)
                    Image(systemName: "heart.fill")
                        .imageScale(.medium)
                        .scaleEffect(1.2)
                        .foregroundColor(.red)
                        .opacity(model.like ? 1: 0)
                        .animation(.easeOut, value: model.like)
                }
            }
            .frame(width: 30, height: 30)
        }
        .padding(10)
        .background(Styles.Colors.white)
        .clipShape(RoundedRectangle(cornerRadius: 10))
        .listRowBackground(Color.clear)
        .listRowSeparator(.hidden)
    }
}

