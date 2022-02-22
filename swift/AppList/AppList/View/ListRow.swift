//
//  ListRow.swift
//  AppList
//
//  Created by jay on 2022/2/22.
//

import SwiftUI

struct ListRow: View {
    
    let item: ListItem
    var favorAction: ((ListItem) -> Void)? = nil
    
    @State private var loading: Bool = true
    
    var body: some View {
        Group {
            HStack {
                
                LoadingImage(loading: loading, imageUrl: item.imageUrl)
                
                VStack(alignment: .leading, spacing: 10) {
                    Text(item.title)
                        .font(Font.system(size: 17))
                        .fontWeight(.bold)
                    Text(item.text)
                        .font(Font.system(size: 12))
                        .fontWeight(.regular)
                        .lineLimit(2)
                }
                
                Spacer()
                
                Image(systemName: item.favored == 1 ? "heart.fill" : "heart")
                    .resizable()
                    .scaledToFit()
                    .frame(width: 22)
                    .foregroundColor(item.favored == 1 ? Color.red : Color("border"))
                    .padding([.vertical, .trailing])
                    .onTapGesture {
                        withAnimation {
                            favorAction?(item)
                        }
                    }
            }
            .padding([.leading, .vertical], 15)
            .padding(.trailing, 8)
            .background(
                RoundedRectangle(cornerRadius: 10)
                    .fill(.white)
            )
        }
        .padding(.horizontal, 17)
        .padding(.vertical, 0)
        .listRowSeparator(.hidden)
        .listRowInsets(SwiftUI.EdgeInsets(top: 5, leading: 0, bottom: 5, trailing: 0))
        .listRowBackground(Color.clear)
    }
}

struct ListRow_Previews: PreviewProvider {
    static var previews: some View {
        let item = DataLoader().getData(of: Page(total: 50)).list.last!
        ListRow(item: item)
    }
}
