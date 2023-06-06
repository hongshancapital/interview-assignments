import SwiftUI

struct AppIconView: View {
    let url: URL
    
    var body: some View {
        AsyncImage(url: url) { image in
            image
                .resizable()
                .scaledToFit()
                .cornerRadius(.CornerRadius.one, antialiased: true)
                .overlay(
                    RoundedRectangle(cornerRadius: .CornerRadius.one,
                                     style: .continuous)
                    .stroke(Color.gray.opacity(0.5), lineWidth: 0.5)
                )
        } placeholder: {
            ProgressView()
        }
        .frame(width: .Length.eight, height: .Length.eight, alignment: .center)
    }
}
