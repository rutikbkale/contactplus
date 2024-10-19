
// import swal from 'sweetalert';

function openViewModal(contactId) {
    const viewContact = document.getElementById("view-contact");

    fetch(`/contactplus/user/contacts/viewContact?contactId=${contactId}`)
        .then(response => response.text())
        .then(html => {
            document.querySelector('#view-contact .modal-body').innerHTML = html;
            viewContact.classList.remove('hidden');
            const options = {
                placement: 'bottom-right',
                backdrop: 'dynamic',
                backdropClasses:
                    'bg-gray-900/50 dark:bg-gray-900/80 fixed inset-0 z-40',
                closable: true,
                onHide: () => {
                    console.log('modal is hidden');
                },
                onShow: () => {
                    console.log('modal is shown');
                },
                onToggle: () => {
                    console.log('modal has been toggled');
                },
            };

            const instanceOptions = {
                id: 'view-contact',
                override: true
            };

            const modal = new Modal(viewContact, options, instanceOptions);
            modal.show();
        })
        .catch(error => console.error('Error fetching contact details:', error));
}

function closeModal() {
    const viewContact = document.getElementById("view-contact");
    viewContact.classList.add('hidden');
}

// deleting contact
function deleteContact(contactId) {
    Swal.fire({
        title: "Do you want to delete the contact?",
        icon: "warning",
        showCancelButton: true,
        confirmButtonText: "Delete",
        cancelButtonText: "Cancel",
        customClass: {
            confirmButton: 'bg-red-600 text-white px-4 py-2 mr-2 rounded-md hover:bg-red-700',
            cancelButton: 'bg-gray-600 text-white px-4 py-2 rounded-md hover:bg-gray-700'
        },
        buttonsStyling: false // Disable default button styling
    }).then((result) => {
        if (result.isConfirmed) {
            // Implement the deletion logic here (e.g., make an AJAX request to delete the contact)
            window.location.href = "delete/" + contactId;
            Swal.fire("Deleted!", "The contact has been deleted.", "success");
        }
    });

}
function openModal() {
    document.getElementById('editModal').classList.remove('hidden');
}

function closeModal() {
    document.getElementById('editModal').classList.add('hidden');
}

