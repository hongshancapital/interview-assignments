//
//  ItemCell.swift
//  Assignment
//
//  Created by Yikai Deng on 2022/6/25.
//

import SwiftUI

struct ItemCell: View {
    
    private struct ErrorMessage: Identifiable {
        var id: String { msg }
        var msg: String
    }
    
    typealias SuccssAction = () -> Void
    typealias FailureAction = (Error) -> Void
    typealias FavoriteAction = (@escaping SuccssAction, @escaping FailureAction) -> Void
    
    private var imageURL: String
    private var title: String
    private var description: String
    @State private var isFavorite: Bool
    
    private var favoriteAction: FavoriteAction
    
    @State private var error: ErrorMessage?
    
    init(imageURL: String?, title: String?, description: String?, isFavorite: Bool, favoriteAction: @escaping FavoriteAction) {
        self.imageURL = imageURL ?? ""
        self.title = title ?? ""
        self.description = description ?? ""
        self.isFavorite = isFavorite
        self.favoriteAction = favoriteAction
    }
    
    var body: some View {
        Group {
            HStack {
                IndicatorImageView(imageURL: imageURL)
                
                VStack(alignment: .leading, spacing: 5) {
                    Text(title)
                        .font(.system(size: 17, weight: .bold))
                    Text(description)
                        .font(.system(size: 15, weight: .light))
                        .lineLimit(2)
                }
                .frame(height: 64)
                
                Spacer()
                
                Image(isFavorite ? "icon_heart_red" : "icon_heart_gray")
                    .resizable()
                    .frame(width: 30, height: 30)
                    .onTapGesture {
                        withAnimation {
                            let success: SuccssAction = {
                                isFavorite = !isFavorite
                            }
                            
                            let failure: FailureAction = { error in
                                self.error = ErrorMessage(msg: error.localizedDescription)
                            }
                            
                            favoriteAction(success, failure)
                        }
                    }
            }
            .padding(.horizontal, 15)
            .padding(.vertical, 15)
            .background(
                RoundedRectangle(cornerRadius: 8)
                    .fill(.white)
            )
        }
        .listRowInsets(EdgeInsets(top: 5, leading: 0, bottom: 5, trailing: 0))
        .padding(.vertical)
        .listRowSeparator(.hidden)
        .listRowBackground(Color.clear)
        .alert(item: $error, content: { error in
            Alert(title: Text("Failed to like"), message: Text(error.msg), dismissButton: .default(Text("Got it")))
        })
    }
}

struct ItemCell_Previews: PreviewProvider {
    static var previews: some View {
        let model = DataProvider.fetchAllData().first!
        ItemCell(imageURL: model.icon, title: model.name, description: model.description, isFavorite: model.isFavorite) { success, failure in
            
        }
    }
}
