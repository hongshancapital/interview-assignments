//
//  ListRow.swift
//  Demo
//
//  Created by 盼 on 2022/4/15.
//

import SwiftUI
import Kingfisher

func loadImage(with url: String?) -> KFImage {
    guard url != nil else {
        return KFImage(source: nil)
    }
    return KFImage(URL(string: url!))
}

struct ListRow: View {
    @EnvironmentObject var homeData: HomeData
    
    var listModel: ListModel
    var listIndex: Int {
        homeData.listArray.firstIndex(where: { $0.id == listModel.id })!
    }
    
    var body: some View {
        HStack {
            loadImage(with: listModel.artworkUrl60)
                .resizable()
                .aspectRatio(contentMode: .fill)
                .frame(width: 60, height: 60)
                .overlay(
                    RoundedRectangle(cornerRadius: 10)
                        .stroke(Color.borderColor, lineWidth: 1)
                )
                .cornerRadius(10)
                .padding([.leading, .top, .bottom], 15)
            
            VStack(alignment: .leading, spacing: 5) {
                Text(listModel.trackCensoredName)
                    .font(.boldFont(18))
                    .lineLimit(1)
                
                Text(listModel.description)
                    .font(.font(14))
                    .lineLimit(2)
            }
            
            Spacer()
            
            Button(action: {
                self.homeData.listArray[listIndex].isGameCenterEnabled.toggle()
            }) {
                Image(listModel.isGameCenterEnabled ? "ic_favourite_sel" : "ic_favourite_unsel")
                    .resizable()
                    .aspectRatio(contentMode: .fit)
                    .frame(width: 20, height: 20)
                    .padding(.trailing, 15)
            }
        }
        .background(Color(.white))
        .frame(width: SCREEN_WIDTH - 30, height: 90)
        .cornerRadius(10)
    }
}

struct ListRow_Previews: PreviewProvider {
    static var previews: some View {
        ListRow(listModel: listData[0])
    }
}
