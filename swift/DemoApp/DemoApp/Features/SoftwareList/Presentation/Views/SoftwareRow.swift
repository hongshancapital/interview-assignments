//
//  SoftwareRow.swift
//  DemoApp
//
//  Created by 黄瑞 on 2023/2/15.
//

import SwiftUI

struct SoftwareRow: View {
    @EnvironmentObject private var viewModel: SoftwareListViewModel
    @Binding var software: Software
    
    var body: some View {
        HStack {
            AsyncImage(
                url: URL(string: software.artworkUrl100)) { image in
                    image.resizable()
                         .aspectRatio(contentMode: .fit)
                         .frame(width: 50, height: 50)
                } placeholder: {
                    ProgressView()
                        .frame(maxWidth: 50, maxHeight: 50)
                }
            Text(software.trackCensoredName)
            Spacer()
            Button {
                software.isLike.toggle()
            } label: {
                Image(systemName: software.isLike ? "star.fill" : "star")
                    .foregroundColor(.yellow)
            }
        }
        .padding()
        .frame(height: 50)
    }
}

struct SoftwareRow_Previews: PreviewProvider {
    static var previews: some View {
        SoftwareRow(software: .constant(Software.default))
            .previewLayout(.fixed(width: 375, height: 50))
    }
}
