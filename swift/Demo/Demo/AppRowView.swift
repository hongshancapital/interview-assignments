//
//  AppRowView.swift
//  Demo
//
//  Created by GNR on 10/27/22.
//

import SwiftUI

struct AppRowView: View {
    var app: AppModel
    var isFavorite: Bool
    var toggleFavorite: (_ app: AppModel) -> Void
    
    var body: some View {
        HStack {
            WebImage(url: app.artworkUrl60)
                .cornerRadius(12.0)
                .frame(width: 60, height: 60)
                        
            VStack(alignment: .leading, spacing: 10) {
                Text(app.trackName)
                    .lineLimit(1)
                    .font(.system(size: 16))
                    .fontWeight(.semibold)
                
                Text(app.description)
                    .lineLimit(2)
                    .font(.system(size: 12))
            }
            
            Spacer()
            
            Image(systemName:isFavorite ? "suit.heart.fill" : "suit.heart")
                .foregroundColor(isFavorite ? .red : .gray)
                .scaleEffect(CGFloat(isFavorite ? 1.4 : 1))
                .animation(.interactiveSpring(), value: isFavorite)
                .onTapGesture {
                    toggleFavorite(self.app)
                }
        }
    }
}

struct AppRowView_Previews: PreviewProvider {
    static var previews: some View {
        Group {
            AppRowView(app: NetWorkManager.shared.mockBackendApps[0], isFavorite: NetWorkManager.shared.mockBackendApps[0].isFavorite, toggleFavorite: { app in
                Task {
                    try? await NetWorkManager.shared.favoriteRequest(app, !app.isFavorite)
                }
            })
            AppRowView(app: NetWorkManager.shared.mockBackendApps[1], isFavorite: NetWorkManager.shared.mockBackendApps[0].isFavorite, toggleFavorite: { app in
                Task {
                    try? await NetWorkManager.shared.favoriteRequest(app, !app.isFavorite)
                }
            })
        }
    }
}
