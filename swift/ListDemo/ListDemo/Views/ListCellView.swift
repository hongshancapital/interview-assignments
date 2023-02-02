//
//  ListCellView.swift
//  ListDemo
//
//  Created by Chr1s on 2022/2/21.
//

import SwiftUI

struct ListCellView: View {
    
    var index: Int
    var cell: ListCell
    @EnvironmentObject var vm: ListViewModel
    
    var body: some View {
        HStack {
            CachedAsyncImage(url: URL(string: cell.artworkUrl60)) { phase in
                switch phase {
                case .empty:
                    ProgressView()
                        .frame(width: 60, height: 60)
                case .success(let image):
                    image.resizable()
                        .aspectRatio(contentMode: .fit)
                        .frame(maxWidth: 60, maxHeight: 60)
                        .clipShape(RoundedRectangle(cornerRadius: 12))
                case .failure:
                    Image(systemName: "photo")
                @unknown default:
                    EmptyView()
                }
            }

            VStack(alignment: .leading, spacing: 3.0) {
                Text(cell.trackName)
                    .lineLimit(1)
                    .font(.title2)

                Text(cell.description).lineLimit(2)
                    .font(.footnote)
                    .offset(x: 2)
            }
            .multilineTextAlignment(.leading)
            .frame(idealWidth: 200, alignment: .leading)
            .fixedSize(horizontal: true, vertical: false)
            
            Spacer()
            
            Image(systemName: vm.isFavorites[index] ? "heart.fill" : "heart")
                .scaleEffect(vm.isFavorites[index] ? 1.4 : 1.2)
                .foregroundColor(vm.isFavorites[index] ? .red : .gray)
                .onTapGesture {
                    vm.updateLikeSubject.send((index,!vm.isFavorites[index]))
                }
        }
        .padding()
        .frame(minWidth: 0, maxWidth: .infinity, alignment: .leading)
        .background(Color.white)
        .cornerRadius(8)
    }
}

struct ListCellView_Previews: PreviewProvider {
    static let s = ApiService()
    static var previews: some View {
        ListCellView(index: 0, cell: ListCell(
            trackId: 0,
            artworkUrl60: "https://is5-ssl.mzstatic.com/image/thumb/Purple126/v4/34/59/8b/34598b2a-4c6d-cec4-a583-da1418c6ba1e/source/60x60bb.jpg",
            trackName: "LiveMe – Live Stream & Go Live",
            description: "LiveMe is a popular live streaming social network. It allows you to live stream your special moments"))
            .environmentObject(ListViewModel(dataService: s))
        
    }
}
