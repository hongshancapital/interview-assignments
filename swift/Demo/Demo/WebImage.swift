//
//  WebImage.swift
//  Demo
//
//  Created by GNR on 10/27/22.
//

import SwiftUI

struct WebImage: View {
    var url: URL
    
    var body: some View {
        VStack {
            AsyncImage(url: url) { image in
                image
                    .resizable()
                    .cornerRadius(12)
            } placeholder: {
                ProgressView()
            }
        }
    }
}

struct WebImage_Previews: PreviewProvider {
    static var previews: some View {
        WebImage(url: NetWorkManager.shared.mockBackendApps.first!.artworkUrl60)
    }
}
