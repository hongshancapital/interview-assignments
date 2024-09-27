//
//  ModelCell.swift
//  SwiftDemo
//
//  Created by xz on 2023/2/10.
//

import SwiftUI

struct ModelCell: View {
    var model: Model
    @State var image: Image?
    @State var isLoading: Bool = false
    var favoriteClickAction: ()->Void
    
    var body: some View {
        HStack {
            AsyncImage(url: URL(string: self.model.artworkUrl100), content: { image in
                image.resizable()
            }, placeholder: {
                ProgressView()
            }).frame(width: 50, height: 50)
                .cornerRadius(8)
                .shadow(radius: 1)
            VStack {
                Text(model.trackName).lineLimit(1).font(.headline).frame(maxWidth: .infinity, alignment: .leading)
                Text(model.description).lineLimit(2).font(.caption).frame(maxWidth: .infinity, alignment: .leading)
            }
            Button {
                self.favoriteClickAction()
            } label: {
                Image(systemName: model.favorite ? "heart.fill" : "heart")
                    .resizable()
                    .frame(width: 20, height: 20)
                    .foregroundColor(model.favorite ? .red : .gray)
                    .scaleEffect(model.favorite ? 1.2 : 1)
                    .animation(.easeInOut, value: model.favorite)
            }.frame(width: 20, height: 20)
                .padding(.zero)
        }.listRowSeparator(.hidden)
            .padding()
            .background(Color.white)
            .cornerRadius(8)
            .shadow(radius: 0.1)
            .listRowBackground(Color.clear)
            .listRowInsets(EdgeInsets(top: 4, leading: 16, bottom: 4, trailing: 16))
    }
}

