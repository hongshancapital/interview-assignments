//
//  AppRow.swift
//  scdtswift
//
//  Created by esafenet on 2023/2/10.
//

import SwiftUI
import SwiftyJSON

struct AppRow: View {
    var data: RowModel
    @State var likeState = false
    var body: some View {
        HStack (alignment: .center) {
            AsyncImage(url: URL(string: data.artworkUrl60))
                .frame(width: 60, height: 60)
                .cornerRadius(8)
                .background {
                    ProgressView()
                }
            VStack(alignment: .leading) {
                Text(data.trackName)
                    .font(.subheadline)
                    .lineLimit(1)
                Text(data.description)
                    .font(.caption)
                    .lineLimit(2)
            }
            Spacer()
            Button {
                likeState.toggle()
            } label: {
                Image(systemName: likeState ? "heart.fill" : "heart")
                    .font(.title)
                    .foregroundColor(likeState ? .pink : .gray)
                    
            }

        }
        .buttonStyle(.borderless)
//        .padding(EdgeInsets(top: 0, leading: 8, bottom: 0, trailing: 0))
    }
}

struct AppRow_Previews: PreviewProvider {
    
    static var previews: some View {
        AppRow(data:appdetailPreviewData)
    }
}
