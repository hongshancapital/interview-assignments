//
//  AppProductItemRow.swift
//  SCDTAppList
//
//  Created by freeblow on 2023/2/14.
//

import SwiftUI

struct AppProductItemRow: View {
    
    @Binding var model: AppProductModel
    
    //according to the response state of AsyncImage, redraw the state of the border of the icon
    @State private(set) var iconLoadStatus: AsynImageLoadingStatus = .loading
    var body: some View {
        HStack{
            AsynIconImage(url: model.icon){ status in
                DispatchQueue.main.async {
                    self.iconLoadStatus = status
                }
            }
                .frame(width: 58.0, height: 58.0)
                .clipShape(RoundedRectangle(cornerRadius: (self.iconLoadStatus != .loading) ? 10 : 0))
                .overlay(content:{
                    if self.iconLoadStatus != .loading{
                        RoundedRectangle(cornerRadius: 10, style: .continuous).stroke(Color("ProductionItemIconRoundBorderColor"), lineWidth: 1.0)
                    }
                })
            VStack(alignment: .leading, spacing: 6){
                Text(model.trackName)
                    .font(.headline)
                    .lineLimit(1)
                    .foregroundColor(.black)
                Text(model.description)
                    .foregroundColor(Color("ProductItemDescriptionTextColor"))
                    .multilineTextAlignment(.leading)
                    .lineLimit(2)
                    .font(.subheadline)
            }
            .frame(maxWidth: .infinity, alignment: .leading)
            Spacer()
            FavoriteButton(isFavorite: $model.isFavorite)
        }
        .padding(.vertical, 15)
        .padding(.horizontal, 15)
        .background(.white)
        .clipShape(
            RoundedRectangle(cornerRadius: 10, style: .continuous)
        )
    }
}

#if DEBUG
struct AppProductItemRow_Previews: PreviewProvider {
    static var previews: some View {
        VStack {
            AppProductItemRow(model: .constant(AppProductModel.preview))
        }
        .padding(.all, 15)
        .background(Color(uiColor: .systemGroupedBackground))
    }
}
#endif
