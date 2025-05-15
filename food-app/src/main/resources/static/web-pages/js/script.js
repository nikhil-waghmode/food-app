// // function loadPage(page) {
// //     console.log('Loading page:', page);  // Debugging line

// //     fetch(page)
// //         .then(response => response.text())
// //         .then(data => {
// //             document.getElementById('content').innerHTML = data;
// //         })
// //         .catch(err => {
// //             document.getElementById('content').innerHTML = '<p class="text-danger">Failed to load content.</p>';
// //         });
// // }
// apiUrl = "http://localhost:3000"

function loadPage(page) {
    fetch(`${page}`)
        .then(response => response.text())
        .then(data => {
            const container = document.getElementById('main-content');
            container.innerHTML = data;

            // Extract and evaluate scripts manually
            const scripts = container.querySelectorAll('script');
            scripts.forEach(script => {
                const newScript = document.createElement('script');
                if (script.src) {
                    newScript.src = script.src;
                } else {
                    newScript.textContent = script.textContent;
                }
                document.body.appendChild(newScript);
                script.remove(); // Optional: remove the original script tag
            });
        })
        .catch(err => {
            document.getElementById('main-content').innerHTML =
                '<p class="text-danger">Failed to load content.</p>';
            console.error(err);
        });
}
// function loadPage(page) {
//     fetch(`${page}`)
//       .then(response => response.text())
//       .then(data => {
//         const container = document.getElementById('main-content');
//         container.innerHTML = data;
  
//         // Extract and re-run inline and external scripts
//         const scripts = container.querySelectorAll('script');
//         scripts.forEach(script => {
//           const newScript = document.createElement('script');
  
//           if (script.src) {
//             // For external script files
//             newScript.src = script.src;
//             newScript.onload = () => {
//               if (typeof window.pageInit === 'function') {
//                 window.pageInit(); // call init if defined in external script
//               }
//             };
//           } else {
//             // For inline scripts
//             newScript.textContent = script.textContent;
//           }
  
//           document.body.appendChild(newScript);
//           script.remove();
//         });
  
//         // 👇 This catches init functions for inline scripts too
//         setTimeout(() => {
//           if (typeof window.pageInit === 'function') {
//             window.pageInit(); // call the init function if defined
//           }
//         }, 0);
//       })
//       .catch(err => {
//         document.getElementById('main-content').innerHTML =
//           '<p class="text-danger">Failed to load content.</p>';
//         console.error(err);
//       });
//   }
  